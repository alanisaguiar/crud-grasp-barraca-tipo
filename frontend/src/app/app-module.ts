import { NgModule, provideBrowserGlobalErrorListeners, provideZoneChangeDetection, } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { ListaTiposBarraca } from './components/tipos-barraca/lista-tipos-barraca/lista-tipos-barraca';
import { FormTipoBarraca } from './components/tipos-barraca/form-tipo-barraca/form-tipo-barraca';
import { ListaBarracas } from './components/barracas/lista-barracas/lista-barracas';
import { FormBarraca } from './components/barracas/form-barraca/form-barraca';

@NgModule({
  declarations: [App, ListaTiposBarraca, FormTipoBarraca, ListaBarracas, FormBarraca],
  imports: [BrowserModule, AppRoutingModule, FormsModule],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideHttpClient(),
  ],
  bootstrap: [App],
})
export class AppModule {}
