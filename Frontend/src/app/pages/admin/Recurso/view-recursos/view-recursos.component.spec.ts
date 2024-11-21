import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewRecursosComponent } from './view-recursos.component';

describe('ViewRecursosComponent', () => {
  let component: ViewRecursosComponent;
  let fixture: ComponentFixture<ViewRecursosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewRecursosComponent]
    });
    fixture = TestBed.createComponent(ViewRecursosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
