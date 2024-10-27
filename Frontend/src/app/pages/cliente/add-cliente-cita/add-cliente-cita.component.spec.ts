import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddClienteCitaComponent } from './add-cliente-cita.component';

describe('AddClienteCitaComponent', () => {
  let component: AddClienteCitaComponent;
  let fixture: ComponentFixture<AddClienteCitaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddClienteCitaComponent]
    });
    fixture = TestBed.createComponent(AddClienteCitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
