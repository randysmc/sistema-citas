import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNegociosComponent } from './add-negocios.component';

describe('AddNegociosComponent', () => {
  let component: AddNegociosComponent;
  let fixture: ComponentFixture<AddNegociosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddNegociosComponent]
    });
    fixture = TestBed.createComponent(AddNegociosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
