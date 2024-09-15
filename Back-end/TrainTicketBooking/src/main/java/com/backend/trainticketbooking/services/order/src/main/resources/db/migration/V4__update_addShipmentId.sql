
alter table orders add column shipment_id int unique;

WITH cte AS (
    SELECT id, row_number() OVER (ORDER BY id) AS shipment_number
    FROM orders
)
UPDATE orders
SET shipment_id = cte.shipment_number
FROM cte
WHERE orders.id = cte.id;


update orders o
set total_price = total_price +
        case
            when o.shipment_id % 2 = 0 then 16.50
            else 13.32
        end
