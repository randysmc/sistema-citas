import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateNegocioComponent } from './update-negocio.component';

describe('UpdateNegocioComponent', () => {
  let component: UpdateNegocioComponent;
  let fixture: ComponentFixture<UpdateNegocioComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UpdateNegocioComponent]
    });
    fixture = TestBed.createComponent(UpdateNegocioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
