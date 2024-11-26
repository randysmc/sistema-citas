import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCitasDetailComponent } from './view-citas-detail.component';

describe('ViewCitasDetailComponent', () => {
  let component: ViewCitasDetailComponent;
  let fixture: ComponentFixture<ViewCitasDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewCitasDetailComponent]
    });
    fixture = TestBed.createComponent(ViewCitasDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
