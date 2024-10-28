import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateRecursoComponent } from './update-recurso.component';

describe('UpdateRecursoComponent', () => {
  let component: UpdateRecursoComponent;
  let fixture: ComponentFixture<UpdateRecursoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateRecursoComponent]
    });
    fixture = TestBed.createComponent(UpdateRecursoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
