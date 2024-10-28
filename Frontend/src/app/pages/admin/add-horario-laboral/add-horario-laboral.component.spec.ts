import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddHorarioLaboralComponent } from './add-horario-laboral.component';

describe('AddHorarioLaboralComponent', () => {
  let component: AddHorarioLaboralComponent;
  let fixture: ComponentFixture<AddHorarioLaboralComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddHorarioLaboralComponent]
    });
    fixture = TestBed.createComponent(AddHorarioLaboralComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
