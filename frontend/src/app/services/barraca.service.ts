import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { Barraca } from '../models/barraca.model';

@Injectable({ providedIn: 'root' })
export class BarracaService {

  private readonly apiUrl = 'http://localhost:8080/api/barracas';

  constructor(private http: HttpClient) {}

  listar(): Observable<Barraca[]> {
    return this.http.get<Barraca[]>(this.apiUrl);
  }

  buscarPorId(id: number): Observable<Barraca> {
    return this.http.get<Barraca>(`${this.apiUrl}/${id}`);
  }

  cadastrar(barraca: Barraca): Observable<Barraca> {
    return this.http.post<Barraca>(this.apiUrl, barraca);
  }

  remover(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}