import { Component } from '@angular/core';

@Component({
  selector: 'app-cliente-dashboard',
  templateUrl: './cliente-dashboard.component.html',
  styleUrls: ['./cliente-dashboard.component.css']
})
export class ClienteDashboardComponent {

  sidebarVisible = true;

  toggleSidebar() {
    this.sidebarVisible = !this.sidebarVisible;
    const sidebar = document.getElementById('sidebar');
    if (sidebar) {
      sidebar.style.display = this.sidebarVisible ? 'block' : 'none';
    }
  }
}
