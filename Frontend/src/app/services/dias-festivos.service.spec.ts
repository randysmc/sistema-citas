import { TestBed } from '@angular/core/testing';

import { DiasFestivosService } from './dias-festivos.service';

describe('DiasFestivosService', () => {
  let service: DiasFestivosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiasFestivosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
