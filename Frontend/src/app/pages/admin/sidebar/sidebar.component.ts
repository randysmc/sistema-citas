import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  @Output() sidebarClosed = new EventEmitter<void>();

  closeSidebar() {
    this.sidebarClosed.emit(); // Emitir un evento cuando se cierra el sidebar
  }
}
