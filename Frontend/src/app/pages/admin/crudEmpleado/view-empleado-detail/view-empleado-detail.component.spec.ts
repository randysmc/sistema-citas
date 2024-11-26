import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEmpleadoDetailComponent } from './view-empleado-detail.component';

describe('ViewEmpleadoDetailComponent', () => {
  let component: ViewEmpleadoDetailComponent;
  let fixture: ComponentFixture<ViewEmpleadoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewEmpleadoDetailComponent]
    });
    fixture = TestBed.createComponent(ViewEmpleadoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
