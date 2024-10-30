import { TestBed } from '@angular/core/testing';

import { ExportacionService } from './exportacion.service';

describe('ExportacionService', () => {
  let service: ExportacionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExportacionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
