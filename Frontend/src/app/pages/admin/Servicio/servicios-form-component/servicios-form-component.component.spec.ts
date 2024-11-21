import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ServiciosFormComponentComponent } from './servicios-form-component.component';

describe('ServiciosFormComponentComponent', () => {
  let component: ServiciosFormComponentComponent;
  let fixture: ComponentFixture<ServiciosFormComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ServiciosFormComponentComponent]
    });
    fixture = TestBed.createComponent(ServiciosFormComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
