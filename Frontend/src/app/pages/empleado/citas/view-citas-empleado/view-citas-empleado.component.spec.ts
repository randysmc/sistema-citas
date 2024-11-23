import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCitasEmpleadoComponent } from './view-citas-empleado.component';

describe('ViewCitasEmpleadoComponent', () => {
  let component: ViewCitasEmpleadoComponent;
  let fixture: ComponentFixture<ViewCitasEmpleadoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewCitasEmpleadoComponent]
    });
    fixture = TestBed.createComponent(ViewCitasEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
