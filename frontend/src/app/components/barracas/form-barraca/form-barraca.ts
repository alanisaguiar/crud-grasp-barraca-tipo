import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Barraca } from '../../../models/barraca.model';
import { TipoBarraca } from '../../../models/tipo-barraca.model';
import { BarracaService } from '../../../services/barraca.service';
import { TipoBarracaService } from '../../../services/tipo-barraca.service';

@Component({
  selector: 'app-form-barraca',
  standalone: false,
  templateUrl: './form-barraca.html',
  styleUrl: './form-barraca.css'
})
export class FormBarraca implements OnInit {

  barraca: Barraca = {
    nome: '',
    descricao: '',
    ativo: true,
    tipoBarracaId: undefined
  };

  tipos: TipoBarraca[] = [];

  salvando = false;
  mensagemErro = '';
  errosCampos: { [campo: string]: string } = {};

  constructor(
    private barracaService: BarracaService,
    private tipoBarracaService: TipoBarracaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarTipos();
  }

  carregarTipos(): void {
    this.tipoBarracaService.listar().subscribe({
      next: (dados) => {
        this.tipos = dados;
      },
      error: () => {
        this.mensagemErro = 'Não foi possível carregar os tipos de barraca. Cadastre um tipo antes.';
      }
    });
  }

  salvar(): void {
    this.salvando = true;
    this.mensagemErro = '';
    this.errosCampos = {};

    // Garante que tipoBarracaId seja número (vem como string do select)
    if (this.barraca.tipoBarracaId) {
      this.barraca.tipoBarracaId = Number(this.barraca.tipoBarracaId);
    }

    this.barracaService.cadastrar(this.barraca).subscribe({
      next: () => {
        this.salvando = false;
        this.router.navigate(['/barracas']);
      },
      error: (e) => {
        this.salvando = false;
        this.tratarErro(e);
      }
    });
  }

  cancelar(): void {
    this.router.navigate(['/barracas']);
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