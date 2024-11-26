import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-horarios',
  templateUrl: './view-horarios.component.html',
  styleUrls: ['./view-horarios.component.css']
})
export class ViewHorariosComponent {

  constructor(private router: Router) {}

  agregarHorario() {
    this.router.navigate(['/admin/add-horario-laboral']);
  }

}
