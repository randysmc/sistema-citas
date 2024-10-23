import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuperuDashboardComponent } from './superu-dashboard.component';

describe('SuperuDashboardComponent', () => {
  let component: SuperuDashboardComponent;
  let fixture: ComponentFixture<SuperuDashboardComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuperuDashboardComponent]
    });
    fixture = TestBed.createComponent(SuperuDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
