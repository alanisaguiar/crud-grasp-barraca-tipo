import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { TipoBarraca } from '../../../models/tipo-barraca.model';
import { TipoBarracaService } from '../../../services/tipo-barraca.service';

@Component({
  selector: 'app-form-tipo-barraca',
  standalone: false,
  templateUrl: './form-tipo-barraca.html',
  styleUrl: './form-tipo-barraca.css'
})
export class FormTipoBarraca {

  tipo: TipoBarraca = {
    nome: '',
    descricao: ''
  };

  salvando = false;
  mensagemErro = '';
  errosCampos: { [campo: string]: string } = {};

  constructor(
    private tipoBarracaService: TipoBarracaService,
    private router: Router
  ) {}

  salvar(): void {
    this.salvando = true;
    this.mensagemErro = '';
    this.errosCampos = {};

    this.tipoBarracaService.cadastrar(this.tipo).subscribe({
      next: () => {
        this.salvando = false;
        this.router.navigate(['/tipos-barraca']);
      },
      error: (e) => {
        this.salvando = false;
        this.tratarErro(e);
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/tipos-barraca']);
  }

  private tratarErro(erro: any): void {
    // Erro de validação Bean Validation (HTTP 400)
    if (erro?.status === 400 && erro?.error?.erros) {
      for (const item of erro.error.erros) {
        this.errosCampos[item.campo] = item.mensagem;
      }
      this.mensagemErro = 'Corrija os campos destacados abaixo.';
      return;
    }

    // Erro de regra de negócio (HTTP 422)
    if (erro?.error?.message) {
      this.mensagemErro = erro.error.message;
      return;
    }

    this.mensagemErro = 'Erro ao salvar. Verifique se o backend está rodando.';
  }
}