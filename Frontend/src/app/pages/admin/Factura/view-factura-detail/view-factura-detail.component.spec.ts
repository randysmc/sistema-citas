import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewFacturaDetailComponent } from './view-factura-detail.component';

describe('ViewFacturaDetailComponent', () => {
  let component: ViewFacturaDetailComponent;
  let fixture: ComponentFixture<ViewFacturaDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewFacturaDetailComponent]
    });
    fixture = TestBed.createComponent(ViewFacturaDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
