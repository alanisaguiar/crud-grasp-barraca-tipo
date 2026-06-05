import { Component, OnInit } from '@angular/core';

import { Barraca } from '../../../models/barraca.model';
import { BarracaService } from '../../../services/barraca.service';

@Component({
  selector: 'app-lista-barracas',
  standalone: false,
  templateUrl: './lista-barracas.html',
  styleUrl: './lista-barracas.css'
})
export class ListaBarracas implements OnInit {

  barracas: Barraca[] = [];
  carregando = false;
  mensagemSucesso = '';
  mensagemErro = '';

  constructor(private barracaService: BarracaService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.mensagemErro = '';

    this.barracaService.listar().subscribe({
      next: (dados) => {
        this.barracas = dados;
        this.carregando = false;
      },
      error: (e) => {
        this.mensagemErro = this.extrairMensagemErro(e);
        this.carregando = false;
      }
    });
  }

  remover(barraca: Barraca): void {
    if (!barraca.id) return;

    const confirmar = confirm(`Tem certeza que deseja remover a barraca "${barraca.nome}"?`);
    if (!confirmar) return;

    this.mensagemSucesso = '';
    this.mensagemErro = '';

    this.barracaService.remover(barraca.id).subscribe({
      next: () => {
        this.mensagemSucesso = `Barraca "${barraca.nome}" removida com sucesso.`;
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