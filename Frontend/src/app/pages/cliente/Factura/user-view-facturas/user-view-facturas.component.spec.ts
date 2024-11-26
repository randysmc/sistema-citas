import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserViewFacturasComponent } from './user-view-facturas.component';

describe('UserViewFacturasComponent', () => {
  let component: UserViewFacturasComponent;
  let fixture: ComponentFixture<UserViewFacturasComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserViewFacturasComponent]
    });
    fixture = TestBed.createComponent(UserViewFacturasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
