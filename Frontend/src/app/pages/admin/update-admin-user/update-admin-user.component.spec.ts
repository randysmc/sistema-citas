import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateAdminUserComponent } from './update-admin-user.component';

describe('UpdateAdminUserComponent', () => {
  let component: UpdateAdminUserComponent;
  let fixture: ComponentFixture<UpdateAdminUserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateAdminUserComponent]
    });
    fixture = TestBed.createComponent(UpdateAdminUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
