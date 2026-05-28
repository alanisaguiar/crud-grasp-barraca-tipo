import { Component, OnInit } from '@angular/core';

import { TipoBarraca } from './models/tipo-barraca.model';
import { TipoBarracaService } from './services/tipo-barraca.service';

@Component({
  selector: 'app-root',
  standalone: false,
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {

  tipos: TipoBarraca[] = [];
  erro = '';
  carregando = false;

  constructor(private tipoBarracaService: TipoBarracaService) {}

  ngOnInit(): void {
    this.carregar();
  }

  carregar(): void {
    this.carregando = true;
    this.erro = '';

    this.tipoBarracaService.listar().subscribe({
      next: (dados) => {
        this.tipos = dados;
        this.carregando = false;
      },
      error: (e) => {
        this.erro = 'Erro ao carregar tipos: ' + (e.message || 'desconhecido');
        this.carregando = false;
        console.error(e);
      }
    });
  }
}