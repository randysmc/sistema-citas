import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewNegocioDetailComponent } from './view-negocio-detail.component';

describe('ViewNegocioDetailComponent', () => {
  let component: ViewNegocioDetailComponent;
  let fixture: ComponentFixture<ViewNegocioDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewNegocioDetailComponent]
    });
    fixture = TestBed.createComponent(ViewNegocioDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
