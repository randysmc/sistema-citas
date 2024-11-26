import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NegocioReportesComponent } from './negocio-reportes.component';

describe('NegocioReportesComponent', () => {
  let component: NegocioReportesComponent;
  let fixture: ComponentFixture<NegocioReportesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NegocioReportesComponent]
    });
    fixture = TestBed.createComponent(NegocioReportesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
