import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserViewCitasComponent } from './user-view-citas.component';

describe('UserViewCitasComponent', () => {
  let component: UserViewCitasComponent;
  let fixture: ComponentFixture<UserViewCitasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserViewCitasComponent]
    });
    fixture = TestBed.createComponent(UserViewCitasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
