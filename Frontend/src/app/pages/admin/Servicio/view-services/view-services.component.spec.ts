import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewServicesComponent } from './view-services.component';

describe('ViewServicesComponent', () => {
  let component: ViewServicesComponent;
  let fixture: ComponentFixture<ViewServicesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewServicesComponent]
    });
    fixture = TestBed.createComponent(ViewServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
