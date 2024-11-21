import { Component } from '@angular/core';

@Component({
  selector: 'app-empleado-dashboard',
  templateUrl: './empleado-dashboard.component.html',
  styleUrls: ['./empleado-dashboard.component.css']
})
export class EmpleadoDashboardComponent {
  sidebarVisible = true;

  toggleSidebar() {
    this.sidebarVisible = !this.sidebarVisible;
    const sidebar = document.getElementById('sidebar');
    if (sidebar) {
      sidebar.style.display = this.sidebarVisible ? 'block' : 'none';
    }
  }
}
