import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEmpleadosComponent } from './view-empleados.component';

describe('ViewEmpleadosComponent', () => {
  let component: ViewEmpleadosComponent;
  let fixture: ComponentFixture<ViewEmpleadosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewEmpleadosComponent]
    });
    fixture = TestBed.createComponent(ViewEmpleadosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
