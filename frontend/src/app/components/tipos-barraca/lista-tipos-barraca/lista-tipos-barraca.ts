import { Component, OnInit } from '@angular/core';

import { TipoBarraca } from '../../../models/tipo-barraca.model';
import { TipoBarracaService } from '../../../services/tipo-barraca.service';

@Component({
  selector: 'app-lista-tipos-barraca',
  standalone: false,
  templateUrl: './lista-tipos-barraca.html',
  styleUrl: './lista-tipos-barraca.css'
})
export class ListaTiposBarraca implements OnInit {

  tipos: TipoBarraca[] = [];
  carregando = false;
  mensagemSucesso = '';
  mensagemErro = '';

  constructor(private tipoBarracaService: TipoBarracaService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.mensagemErro = '';

    this.tipoBarracaService.listar().subscribe({
      next: (dados) => {
        this.tipos = dados;
        this.carregando = false;
      },
      error: (e) => {
        this.mensagemErro = this.extrairMensagemErro(e);
        this.carregando = false;
      }
    });
  }

  remover(tipo: TipoBarraca): void {
    if (!tipo.id) return;

    const confirmar = confirm(`Tem certeza que deseja remover o tipo "${tipo.nome}"?`);
    if (!confirmar) return;

    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.tipoBarracaService.remover(tipo.id).subscribe({
      next: () => {
        this.mensagemSucesso = `Tipo "${tipo.nome}" removido com sucesso.`;
        this.carregar();
      },
      error: (e) => {
        this.mensagemErro = this.extrairMensagemErro(e);
      }
    });
  }

  private extrairMensagemErro(erro: any): string {
    if (erro?.error?.message) {
      return erro.error.message;
    }
    return 'Erro ao processar a requisição. Verifique se o backend está rodando.';
  }
}