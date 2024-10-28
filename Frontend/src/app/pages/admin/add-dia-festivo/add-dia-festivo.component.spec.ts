import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDiaFestivoComponent } from './add-dia-festivo.component';

describe('AddDiaFestivoComponent', () => {
  let component: AddDiaFestivoComponent;
  let fixture: ComponentFixture<AddDiaFestivoComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddDiaFestivoComponent]
    });
    fixture = TestBed.createComponent(AddDiaFestivoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
