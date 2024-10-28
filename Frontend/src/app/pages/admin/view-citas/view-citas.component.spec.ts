import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewCitasComponent } from './view-citas.component';

describe('ViewCitasComponent', () => {
  let component: ViewCitasComponent;
  let fixture: ComponentFixture<ViewCitasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewCitasComponent]
    });
    fixture = TestBed.createComponent(ViewCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
