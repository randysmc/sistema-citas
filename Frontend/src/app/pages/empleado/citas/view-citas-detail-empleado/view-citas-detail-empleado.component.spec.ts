import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCitasDetailEmpleadoComponent } from './view-citas-detail-empleado.component';

describe('ViewCitasDetailEmpleadoComponent', () => {
  let component: ViewCitasDetailEmpleadoComponent;
  let fixture: ComponentFixture<ViewCitasDetailEmpleadoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewCitasDetailEmpleadoComponent]
    });
    fixture = TestBed.createComponent(ViewCitasDetailEmpleadoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
