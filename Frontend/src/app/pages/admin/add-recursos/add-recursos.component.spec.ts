import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRecursosComponent } from './add-recursos.component';

describe('AddRecursosComponent', () => {
  let component: AddRecursosComponent;
  let fixture: ComponentFixture<AddRecursosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddRecursosComponent]
    });
    fixture = TestBed.createComponent(AddRecursosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
