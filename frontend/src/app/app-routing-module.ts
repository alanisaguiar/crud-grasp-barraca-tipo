import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { ListaTiposBarraca } from './components/tipos-barraca/lista-tipos-barraca/lista-tipos-barraca';
import { FormTipoBarraca } from './components/tipos-barraca/form-tipo-barraca/form-tipo-barraca';
import { ListaBarracas } from './components/barracas/lista-barracas/lista-barracas';
import { FormBarraca } from './components/barracas/form-barraca/form-barraca';

const routes: Routes = [
  { path: '', redirectTo: '/barracas', pathMatch: 'full' },

  { path: 'barracas', component: ListaBarracas },
  { path: 'barracas/nova', component: FormBarraca },

  { path: 'tipos-barraca', component: ListaTiposBarraca },
  { path: 'tipos-barraca/novo', component: FormTipoBarraca },

  { path: '**', redirectTo: '/barracas' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }