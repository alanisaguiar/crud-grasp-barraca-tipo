import { TipoBarraca } from './tipo-barraca.model';

export interface Barraca {
  id?: number;
  nome: string;
  descricao?: string;
  ativo: boolean;
  tipoBarraca?: TipoBarraca;
  tipoBarracaId?: number;
}