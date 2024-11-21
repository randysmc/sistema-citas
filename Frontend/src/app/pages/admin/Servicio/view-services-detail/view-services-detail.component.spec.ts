import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewServicesDetailComponent } from './view-services-detail.component';

describe('ViewServicesDetailComponent', () => {
  let component: ViewServicesDetailComponent;
  let fixture: ComponentFixture<ViewServicesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewServicesDetailComponent]
    });
    fixture = TestBed.createComponent(ViewServicesDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
