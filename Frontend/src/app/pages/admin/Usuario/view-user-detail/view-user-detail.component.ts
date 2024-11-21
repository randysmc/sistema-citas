import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-view-user-detail',
  templateUrl: './view-user-detail.component.html',
  styleUrls: ['./view-user-detail.component.css']
})
export class ViewUserDetailComponent implements OnInit {
  user: any = null;
  id: number = 0;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.id = this.route.snapshot.params['id'];
    this.cargarUsuario(this.id);
  }

  cargarUsuario(id: number) {
    this.userService.obtenerUsuarioPorId(id).subscribe(
      (dato: any) => {
        this.user = dato;
      },
      (error) => {
        console.log(error);
        Swal.fire('Error', 'Error al cargar el recurso', 'error');
      }
    );
  }



  
}
