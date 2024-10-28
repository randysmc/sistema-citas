import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateClienteUserComponent } from './update-cliente-user.component';

describe('UpdateClienteUserComponent', () => {
  let component: UpdateClienteUserComponent;
  let fixture: ComponentFixture<UpdateClienteUserComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateClienteUserComponent]
    });
    fixture = TestBed.createComponent(UpdateClienteUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
