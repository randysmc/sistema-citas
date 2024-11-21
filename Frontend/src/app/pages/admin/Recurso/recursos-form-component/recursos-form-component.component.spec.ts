import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecursosFormComponentComponent } from './recursos-form-component.component';

describe('RecursosFormComponentComponent', () => {
  let component: RecursosFormComponentComponent;
  let fixture: ComponentFixture<RecursosFormComponentComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RecursosFormComponentComponent]
    });
    fixture = TestBed.createComponent(RecursosFormComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
