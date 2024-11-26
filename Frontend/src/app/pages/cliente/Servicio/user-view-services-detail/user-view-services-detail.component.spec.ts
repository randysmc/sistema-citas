import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserViewServicesDetailComponent } from './user-view-services-detail.component';

describe('UserViewServicesDetailComponent', () => {
  let component: UserViewServicesDetailComponent;
  let fixture: ComponentFixture<UserViewServicesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserViewServicesDetailComponent]
    });
    fixture = TestBed.createComponent(UserViewServicesDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
