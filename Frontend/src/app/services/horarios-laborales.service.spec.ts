import { TestBed } from '@angular/core/testing';

import { HorariosLaboralesService } from './horarios-laborales.service';

describe('HorariosLaboralesService', () => {
  let service: HorariosLaboralesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HorariosLaboralesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
