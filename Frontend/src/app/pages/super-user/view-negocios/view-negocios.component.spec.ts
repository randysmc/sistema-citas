import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewNegociosComponent } from './view-negocios.component';

describe('ViewNegociosComponent', () => {
  let component: ViewNegociosComponent;
  let fixture: ComponentFixture<ViewNegociosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewNegociosComponent]
    });
    fixture = TestBed.createComponent(ViewNegociosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
