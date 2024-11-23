import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpleadoUpdateProfileComponent } from './empleado-update-profile.component';

describe('EmpleadoUpdateProfileComponent', () => {
  let component: EmpleadoUpdateProfileComponent;
  let fixture: ComponentFixture<EmpleadoUpdateProfileComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmpleadoUpdateProfileComponent]
    });
    fixture = TestBed.createComponent(EmpleadoUpdateProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
