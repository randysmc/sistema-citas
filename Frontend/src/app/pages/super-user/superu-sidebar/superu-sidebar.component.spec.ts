import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SuperuSidebarComponent } from './superu-sidebar.component';

describe('SuperuSidebarComponent', () => {
  let component: SuperuSidebarComponent;
  let fixture: ComponentFixture<SuperuSidebarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SuperuSidebarComponent]
    });
    fixture = TestBed.createComponent(SuperuSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
