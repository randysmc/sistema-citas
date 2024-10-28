import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewUserDetailComponent } from './view-user-detail.component';

describe('ViewUserDetailComponent', () => {
  let component: ViewUserDetailComponent;
  let fixture: ComponentFixture<ViewUserDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ViewUserDetailComponent]
    });
    fixture = TestBed.createComponent(ViewUserDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
