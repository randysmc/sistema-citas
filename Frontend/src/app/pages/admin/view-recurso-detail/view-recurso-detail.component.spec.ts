import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewRecursoDetailComponent } from './view-recurso-detail.component';

describe('ViewRecursoDetailComponent', () => {
  let component: ViewRecursoDetailComponent;
  let fixture: ComponentFixture<ViewRecursoDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewRecursoDetailComponent]
    });
    fixture = TestBed.createComponent(ViewRecursoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
