import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserViewFacturasDetailComponent } from './user-view-facturas-detail.component';

describe('UserViewFacturasDetailComponent', () => {
  let component: UserViewFacturasDetailComponent;
  let fixture: ComponentFixture<UserViewFacturasDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserViewFacturasDetailComponent]
    });
    fixture = TestBed.createComponent(UserViewFacturasDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
