import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserViewCitasDetailComponent } from './user-view-citas-detail.component';

describe('UserViewCitasDetailComponent', () => {
  let component: UserViewCitasDetailComponent;
  let fixture: ComponentFixture<UserViewCitasDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserViewCitasDetailComponent]
    });
    fixture = TestBed.createComponent(UserViewCitasDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
