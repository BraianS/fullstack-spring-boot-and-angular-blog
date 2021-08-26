import { CommonModule } from '@angular/common';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ErrorInterceptor } from './interceptors/ErrorInterceptor';
import { JwtInterceptor } from './interceptors/JwtInterceptor';
import { NotFoundModule } from './modules/not-found/not-found.module';

@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule,
    NotFoundModule
  ],
  exports: [
    CommonModule,
    NotFoundModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: JwtInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    }
  ]
})
export class CoreModule { }
