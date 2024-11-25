import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewHorarioDetailComponent } from './view-horario-detail.component';

describe('ViewHorarioDetailComponent', () => {
  let component: ViewHorarioDetailComponent;
  let fixture: ComponentFixture<ViewHorarioDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewHorarioDetailComponent]
    });
    fixture = TestBed.createComponent(ViewHorarioDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
