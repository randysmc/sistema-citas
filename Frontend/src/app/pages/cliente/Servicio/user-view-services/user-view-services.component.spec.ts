import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserViewServicesComponent } from './user-view-services.component';

describe('UserViewServicesComponent', () => {
  let component: UserViewServicesComponent;
  let fixture: ComponentFixture<UserViewServicesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserViewServicesComponent]
    });
    fixture = TestBed.createComponent(UserViewServicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
