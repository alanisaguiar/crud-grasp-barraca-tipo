import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { TipoBarraca } from '../models/tipo-barraca.model';

@Injectable({ providedIn: 'root' })
export class TipoBarracaService {

  private readonly apiUrl = 'http://localhost:8080/api/tipos-barraca';

  constructor(private http: HttpClient) {}

  listar(): Observable<TipoBarraca[]> {
    return this.http.get<TipoBarraca[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<TipoBarraca> {
    return this.http.get<TipoBarraca>(`${this.apiUrl}/${id}`);
  }

  cadastrar(tipo: TipoBarraca): Observable<TipoBarraca> {
    return this.http.post<TipoBarraca>(this.apiUrl, tipo);
  }

  remover(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}