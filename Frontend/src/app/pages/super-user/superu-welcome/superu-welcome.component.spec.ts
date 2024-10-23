import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuperuWelcomeComponent } from './superu-welcome.component';

describe('SuperuWelcomeComponent', () => {
  let component: SuperuWelcomeComponent;
  let fixture: ComponentFixture<SuperuWelcomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuperuWelcomeComponent]
    });
    fixture = TestBed.createComponent(SuperuWelcomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
